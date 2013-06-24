import web, json
from google.appengine.ext import db
from google.appengine.ext.webapp.util import run_wsgi_app

urls = (
	'/query/category/(.*)', 'category',
	'/query/.*', 'index'
)
# Its important to define the class... Or else the script crashes with "internal server error"
class Event(db.Model):
  event_cal = db.StringProperty()
  event_summary = db.StringProperty()

class category:
	def GET(self, category):
		clubs_list = {}
		if category.lower() == "all" or category.lower() == "":
			clubs_list = db.GqlQuery("SELECT * FROM Event")
		else:
			clubs_list = db.GqlQuery("SELECT * FROM Event WHERE event_cal = :1", category)
		
		clubs_info_list = []
		
		if clubs_list is None:
			return "The category you're looking for is not available"

		for every_clubs in clubs_list:
			club_info = {}
			club_info["summary"] = every_clubs.event_summary
			"""club_info["id"] = every_clubs.id
			club_info["name"] = every_clubs.club_name
			club_info["category"] = every_clubs.club_category
			club_info["data_changed"] = every_clubs.club_dchange"""
			clubs_info_list.append(club_info)

		return json.dumps(clubs_info_list)
		# return clubs_list[0]

class index:
	def GET(self):
		return "Please enter a category if you want to see something usefull"

app = web.application(urls, globals())
# New(key_name='TEST2', n_int=1).put()
# db = web.database(dbn='mysql', user='pranayk', pw='pjain', db='events', host='localhost')
if __name__ == "__main__":
# app.gaerun()
  application = app.wsgifunc()
  run_wsgi_app(application)
