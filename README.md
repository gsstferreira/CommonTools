# CommonTools
Packages for common Java utilities

## Http Tools
```
import HttpTools.*;
...
HttpClient client  = new HttpClient(...);
...
HttpResponse response = client.sendGetRequest(...);
...
```
### Methods

HttpClient ->

- **HttpClient**(boolean *useCache*, boolean *proxy*, boolean *secure*, int *requestTimeout*, int *responseTimeout*)
- **HttpResponse** sendGetRequest(String *url*, List<Header> *headers*)
- **HttpResponse** sendPostRequest(String *url*, List<Header> *headers*, byte[] *data*)
- **HttpResponse** sendPutrequest(String *url*, List<Header> *headers*, byte[] *data*)
- **HttpResponse** sendDeleteRequest(String *url*, List<Header> *headers*)


[DownloadHttp Tools (jar)](https://github.com/gsstferreira/CommonTools/raw/master/out/artifacts/HttpTool/HttpTool.jar)

## Base64 Tools
```
import Base64Tools.*;
...
String base64String = Base64Tool.encodeBytes(...);
...
byte[] bytes = Base64Tools.decodeBytes(...);
```

### Methods

Base64Tool ->

- **String** encodeBytes(byte[] *bytes*, boolean *isUrlEncoded*)
- **String** encodeString(String *string*, boolan *isUrlEncoded*)
- **byte[]** decodeBytes(byte[] *bytes*, boolean *isUrlEncoded*)
- **byte[]** decodeString(String *string*, boolean *isUrlEncoded*)


[Download Base64 Tools (jar)](https://github.com/gsstferreira/CommonTools/raw/master/out/artifacts/Base64Tool/Base64Tool.jar)
