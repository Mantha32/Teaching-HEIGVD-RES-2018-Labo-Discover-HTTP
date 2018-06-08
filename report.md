# Requirement

clean the repository:
`mvn clean install`

## Setup the dockerised server
Dont forget to copy the image in the right repository
- built the image:
`docker build -t res/discover-http .`
- run a container :
```bash
docker run --name server 8080
docker run --name server
```


## Setup dockerised client
Thanks to environment variable, we can setup our client
- built the image:
`docker build -t res/discover-http .`
- run a container that mimics GET:
```bash
docker run --name client1 res/client-http 172.17.0.2 8080 GET / text/html
docker run --name client1 res/client-http 172.17.0.2 8080 GET / application/json
docker run --name client1 res/client-http 172.17.0.2 8080 GET / application/xml
```
- run a container that mimics POST:
```bash
docker run --name client1 res/client-http 172.17.0.2 8080 POST / text/html 15:45
docker run --name client1 res/client-http 172.17.0.2 8080 POST / application/json '{"hour":11,"minute":53}'
docker run --name client1 res/client-http 172.17.0.2 8080 POST / application/xml "<Hours><hour>12</hour><minute>21</minute></Hours>"
```

## Testing the server
### Using **curl**
` curl http://127.0.0.1:8080`

### Using **Postman** client

### Using **telnet** client
#### **GET** request
```bash
telnet 127.0.0.1 8080
GET / HTTP/1.1
Accept: text/html,application/xhtml+xml,application/xml;q=0.9;q=0.8
```

#### **POST** request
- with content-type ***text/html***

```bash
telnet 127.0.0.1 8080
POST / HTTP/1.1
Host: localhost
User-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5 (.NET CLR 3.5.30729)
Accept: text/html,application/xhtml+xml,application/xml;q=0.9;q=0.8
Accept-Language: en-us,en;q=0.5
Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7
Content-Type: application/x-www-form-urlencoded
Content-Length: 5

15:45
```

- with content-type ***application/json***

```bash
telnet 127.0.0.1 8080
POST / HTTP/1.1
Host: localhost
User-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5 (.NET CLR 3.5.30729)
Accept: application/json
Accept-Language: en-us,en;q=0.5
Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7
Content-Type: application/x-www-form-urlencoded
Content-Length: 28

{"hour":12,"minute":21}
```
- with content-type ***application/xml***

```bash
telnet 127.0.0.1 8080
POST / HTTP/1.1
Host: localhost
User-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5 (.NET CLR 3.5.30729)
Accept: application/xml
Accept-Language: en-us,en;q=0.5
Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7
Content-Type: application/x-www-form-urlencoded
Content-Length: 28

<Hours><hour>12</hour><minute>21</minute></Hours>
```
