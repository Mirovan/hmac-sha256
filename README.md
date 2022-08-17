# Механизм подтверждения подписи HMAC-SHA256

**Пример использования.**

POST http://localhost:8080/api/hello2?clientId=111&date=1654253084

Authorization: API-KEY
- Key: hmac-sign
- Sign: 5EgWzg5/6JBpebElhgYE1g3JUUMy/DuUxbhBXtuexOI= 
- Add to: Header


Body:
````
{
"companyId": "111",
"individualData": {
"lastName": "Ермаков",
"firstName": "Сергей",
"patronymic": "Викторович",
"inn": "271700016569"
}
}
````