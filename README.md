app.jwt-secret=daf66e01593f61a15b857cf433aae03a005812b31234e149036bcc8dee755dbb
daf66e01593f61a15b857cf433aae03a005812b31234e149036bcc8dee755dbb is encoded and decoded key name is techgen and
can be encoded using online https://emn178.github.io/online-tools/sha256.html

app-jwt-expiration-milliseconds=120000 i.e valid for 2 mins

JWT development steps:->
1)Add JWT dependency
2)Create JwtAuthEntryPoint i.e. exception handling
3)Add jwt prop in app.prop file
4)Create JwtTokenGenerator
5)Create JwtAuthenticationFilter
6)Create JWTAuthResp DTO
7)Configure JWT in spring sec config
8)Change login API to return token to client

Also includes DB Scripts and Postman-collection