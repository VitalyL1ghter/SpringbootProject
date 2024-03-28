insert into customer (customer_id, first_name, last_name, address_mail, phone_number, city, street,
                     house_number_apartment_number,
                     soft_deleted)
values (1, 'Виталий', 'Попцов', 'vit@mail.ru', '891790001', 'Казань', 'Гвардейская', '35/3', false),
       (2, 'Василий', 'Васяткин', 'vas@mail.ru', '891790002', 'Казань', 'Гвардейская', '35/3', false),
       (3, 'Николай', 'Терешин', 'nik@mail.ru', '891790003', 'Казань', 'Гвардейская', '35/3', false),
       (4, 'Елизавета', 'Никомирова', 'lis@mail.ru', '891790004', 'Казань', 'Гвардейская', '35/3', false),
       (5, 'Мария', 'Петрова', 'mar@yandex.ru', '891790005', 'Казань', 'Гвардейская', '35/3', false);

insert into pizza (pizza_id, pizza_name, ingredients, size, cost_pizza)
values (1, 'Маргарита', 'Помидоры, пицца соус, сыр моцарелла, соус цезарь', '32 см', 350),
       (2, 'Маргарита', 'Помидоры, пицца соус, сыр моцарелла, соус цезарь', '40 см', 450),
       (3, 'Пепперони', 'Соус пицца, колбаски пепперони, сыр пармезан, шампиньоны', '32 см', 450),
       (4, 'Пепперони', 'Соус пицца, колбаски пепперони, сыр пармезан, шампиньоны', '40 см', 500),
       (5, 'Салями', 'Соус пицца, колбаски салями, сыр пармезан, сыр гауда, шампиньоны, маслины', '32 см', 550);

insert into order_pizza(order_pizza_id, customer_id, pizza_id, amount, date_order, order_price)
values
    (1,1,1,2,localtimestamp,700),
       (2,1,2,3,localtimestamp, 1350),
       (3,2,3,1,localtimestamp,450),
(4,3,4,1,localtimestamp,500),
    (5,3,5,2,localtimestamp,1100);








