import web
import query

urls=(
        '/','index',
        '/loc','loc'
        )

class index:
    def GET(self):
        tmpl = web.template.render("tmpl/")
        return tmpl.index()

    def POST(self):
        data = web.data()
        if data.split(",")[0].split("=")[0] == "ACCELEROMETER":
        #print data.split(",")[0].split("=")[0]
            print "_________________"
            print data.split(",")[0].split("=")[1].split("%2C")
            print "_________________"
        return "HIHI"

class loc:
    def GET(self):
        db = query.init_Mongo()
        return query.query_one_loc(db)

    def POST(self):
        return "This post:)"


if __name__ == "__main__":
    app = web.application(urls,globals())
    app.run()
