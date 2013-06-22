import web, json

urls = (
	'/category/(.*)', 'category',
	'/', 'index'
)

class category:
	def GET(self, category):
		clubs_list = {}
		if category.lower() == "all" or category.lower() == "":
			clubs_list = db.query("select * from clubs_list")
		else:
			clubs_list = db.query("select * from clubs_list where clubs_list.club_category = $category", vars={'category':category})
		
		clubs_info_list = []
		
		if len(clubs_list) == 0:
			return "The category you're looking for is not available"

		for every_clubs in clubs_list:
			club_info = {}
			club_info["id"] = every_clubs.id
			club_info["name"] = every_clubs.club_name
			club_info["category"] = every_clubs.club_category
			club_info["data_changed"] = every_clubs.club_dchange
			clubs_info_list.append(club_info)

		return json.dumps(clubs_info_list)

class index:
	def GET(self):
		return "Please enter a category if you want to see something usefull"

db = web.database(dbn='mysql', user='pranayk', pw='pjain', db='events', host='localhost')

if __name__ == "__main__":
	app = web.application(urls, globals())
	app.run()
