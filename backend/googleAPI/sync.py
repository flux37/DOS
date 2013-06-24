#!/usr/bin/env python
#
# Copyright 2012 Google Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

import httplib2
import logging
import os
import pickle
import time
import datetime

from rfc3339 import rfc3339
from apiclient.discovery import build
from oauth2client.appengine import oauth2decorator_from_clientsecrets
from oauth2client.client import AccessTokenRefreshError
from google.appengine.api import memcache
from google.appengine.ext import webapp
from google.appengine.ext.webapp import template
from google.appengine.ext.webapp.util import run_wsgi_app
from google.appengine.ext import db

class Event(db.Model):
  event_cal = db.StringProperty()
  event_summary = db.StringProperty()

class Time(db.Model):
  last_time = db.DateTimeProperty()

# Time of the previous execution of the script in RFC3339 format.
query = Time.all()
query.filter("key_name =", 'ScriptRunTime')
timevar = query.get()
# timevar = db.GqlQuery("SELECT * FROM Time WHERE key_name = 'ScriptRunTime'")

dtstamp = datetime.datetime.fromtimestamp(time.time())
Time(key_name = 'ScriptRunTime', last_time = dtstamp).put()

if timevar is None:
  update_time = rfc3339(dtstamp)
else:
  update_time = rfc3339(timevar.last_time)
# update_time = rfc3339(dtstamp)

# CLIENT_SECRETS, name of a file containing the OAuth 2.0 information for this
# application, including client_id and client_secret.
# You can see the Client ID and Client secret on the API Access tab on the
# Google APIs Console <https://code.google.com/apis/console>
CLIENT_SECRETS = os.path.join(os.path.dirname(__file__), 'client_secrets.json')

# Helpful message to display in the browser if the CLIENT_SECRETS file
# is missing.
MISSING_CLIENT_SECRETS_MESSAGE = """
<h1>Warning: Please configure OAuth 2.0</h1>
<p>
To make this sample run you will need to populate the client_secrets.json file
found at:
</p>
<code>%s</code>
<p>You can find the Client ID and Client secret values
on the API Access tab in the <a
href="https://code.google.com/apis/console">APIs Console</a>.
</p>

""" % CLIENT_SECRETS


http = httplib2.Http(memcache)
service = build("calendar", "v3", http=http,
    developerKey='AIzaSyC_0sFRn1Z8VrqQ-vxbou0ALWl1Mt9sStY')

# Set up an OAuth2Decorator object to be used for authentication.  Add one or
# more of the following scopes in the scopes parameter below. PLEASE ONLY ADD
# THE SCOPES YOU NEED. For more information on using scopes please see
# <https://developers.google.com/+/best-practices>.
# 'https://www.googleapis.com/auth/calendar'

decorator = oauth2decorator_from_clientsecrets(
    CLIENT_SECRETS,
    scope=[
      'https://www.googleapis.com/auth/calendar.readonly',
    ],
    message=MISSING_CLIENT_SECRETS_MESSAGE)

class MainHandler(webapp.RequestHandler):

  @decorator.oauth_required
  def get(self):
    http = decorator.http()
    try:
      page_token = None
      while True:
        calendar_list = service.calendarList().list(pageToken=page_token).execute(http=http)
        if calendar_list['items']:
          for calendar_list_entry in calendar_list['items']:
            # self.response.out.write("""<html><body><br>""" + """<p>""" + calendar_list_entry['summary'] + """</p>""" + """<br>""")
            page_token_two = None
            while True:
              events = service.events().list(calendarId=calendar_list_entry['id'], updatedMin=update_time, pageToken=page_token_two).execute(http=http)
              if events['items']:
                for event in events['items']:
                  self.response.out.write(event['summary'] + """<html><body><br>""")
                  Event(key_name=event['id'], event_summary=event['summary'], event_cal=calendar_list_entry['summary']).put()
              page_token_two = events.get('nextPageToken')
              if not page_token_two:
                break
        page_token = calendar_list.get('nextPageToken')
        if not page_token:
          break

    except AccessTokenRefreshError:
      self.response.out.write("""<html><body><p>The credentials have been revoked or expired, please re-run"
        "the application to re-authorize</p>""")
    self.response.out.write(dtstamp)
    self.response.out.write("""<html><body><p>Sync Complete!</p>""")

def main():
  application = webapp.WSGIApplication(
      [
       ('/sync', MainHandler),
       (decorator.callback_path, decorator.callback_handler()),
      ],
      debug=True)
  run_wsgi_app(application)

if __name__ == '__main__':
  main()
