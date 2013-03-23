import web
import query

urls=(
        '/','index',
        '/loc','loc'
        )

class index:
    def GET(self):
        return "HELLO"

    def POST(self):
        data = web.data()
        print data.split(",")[0].split("=")[0]
        print data.split(",")[0].split("=")[1].split("%2C")
        return "HIHI"

class loc:
    def GET(self):
        db = query.init_Mongo()
        return query.query_post(db)

    def POST(self):
        return "This post:)"


if __name__ == "__main__":
    app = web.application(urls,globals())
    app.run()
