<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <title>Покупатели</title>
</head>
<body>
<table>
    <tr>
        <th>Идентификатор</th>
        <th>Фамилия</th>
        <th>Имя</th>
        <th>Номер телефона</th>
        <th>E-MAIL</th>
        <th>Флаг удаления</th>
    </tr>
    <#list customers as customer>
        <tr>
            <td>${customer.id}</td>
            <td>${customer.lastName}</td>
            <td>${customer.firstName}</td>
            <td>${customer.phoneNumber}</td>
            <td>${customer.adressMail}</td>
            <td>${customer.softDeletedFlag}</td>
        </tr>
    </#list>
</table>
</body>
</html>