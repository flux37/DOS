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
import webapp2
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
from oauth2client.appengine import AppAssertionCredentials
from google.appengine.api import memcache
from google.appengine.ext.webapp import template
from google.appengine.ext.webapp.util import run_wsgi_app
from google.appengine.ext import db

api_key = 'AIzaSyDjY0v6wL2_nKcznYK3UxQSkxKsdX89BR8'

class Event(db.Model):
  title = db.StringProperty()
  des = db.TextProperty()
  time = db.DateTimeProperty()
  location = db.TextProperty()
  creator = db.UserProperty()
  event_cal = db.StringProperty()
#  event_summary = db.StringProperty()

class Time(db.Model):
  last_time = db.DateTimeProperty()

# Time of the previous execution of the script in RFC3339 format.
query = Time.all()
query.filter("key_name =", 'ScriptRunTime')
timevar = query.get()

dtstamp = datetime.datetime.fromtimestamp(time.time())
Time(key_name = 'ScriptRunTime', last_time = dtstamp).put()

if timevar is None:
  update_time = rfc3339(dtstamp)
else:
  update_time = rfc3339(timevar.last_time)

credentials = AppAssertionCredentials(scope=[
      'https://www.googleapis.com/auth/calendar.readonly',
	  'https://www.googleapis.com/auth/calendar',
    ])
http = credentials.authorize(httplib2.Http())
service = build("calendar", "v3", http=http,
    developerKey=api_key)

class MainHandler(webapp2.RequestHandler):

  def get(self):
    try:
      page_token = None
      while True:
        calendar_list = service.calendarList().list(pageToken=page_token).execute(http=http)
        self.response.out.write(calendar_list)
        if calendar_list['items']:
          self.response.out.write('<html><body><p>Debug = 1</p>')
          for calendar_list_entry in calendar_list['items']:
            self.response.out.write("""<html><body><br>""" + """<p>""" + calendar_list_entry['summary'] + """</p>""" + """<br>""")
            page_token_two = None
            while True:
              events = service.events().list(calendarId=calendar_list_entry['id'], pageToken=page_token_two).execute(http=http)
              if events['items']:
                self.response.out.write('<html><body><p>Debug = 2</p>')
                for event in events['items']:
                  self.response.out.write(event['summary'] + '<html><body><br>')
                  Event(key_name = event['id'], title = event['summary'], event_cal = calendar_list_entry['summary']).put()
              page_token_two = events.get('nextPageToken')
              if not page_token_two:
                break
        page_token = calendar_list.get('nextPageToken')
        if not page_token:
          break
      self.response.out.write(dtstamp)
      self.response.out.write('<html><body><p>Sync Complete!</p>')

    except AccessTokenRefreshError:
      self.response.out.write('<html><body><p>The credentials have been revoked or expired, please re-run'
        'the application to re-authorize</p>')

application = webapp2.WSGIApplication(
    [
     ('/sync', MainHandler),
    ],
    debug=True)
