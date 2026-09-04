const http = require('http')

const server = http.createServer((req,res) => {
    res.writeHead(200 , {"Content-Type": "text/html"});

    res.end(`<html> 
            <head>
            <title>Node.js Docker application </title>
            </head>
            <body>
              <h1>Hello world from Nodejs and docker </h1>
            </body>
        </html>`)
})


server.listen(3000 , "0.0.0.0" , ()=>{
    console.log("server is running on port 3000")
})