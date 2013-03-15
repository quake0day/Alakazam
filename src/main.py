import web

urls=(
        '/','index'
        )

class index:
    def GET(self):
        return "HELLO"

    def POST(self):
        data = web.data()
        print data.split(",")[0].split("=")[0]
        print data.split(",")[0].split("=")[1].split("%2C")
        return "HIHI"


if __name__ == "__main__":
    app = web.application(urls,globals())
    app.run()
