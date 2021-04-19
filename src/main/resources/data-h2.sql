-- USER
INSERT INTO users(name, email, password)
VALUES ('junseok', 'test@gmail.com',
        '1ARVn2Auq2/WAqx2gNrL+q3RNjAzXpUfCXrzkA6d4Xa22yhRLy4AC50E+6UTPoscbo31nbOoq51gvkuXzJ6B2w==') -- user id 1
;

-- STORE
INSERT INTO stores(name, store_state, off_day, is_run_24, open_time, close_time)
VALUES ('Normal Store1', 'NORMAL', 7, false, 540, 1080), -- store id 1
       ('Hidden Store', 'HIDDEN', 7, false, 540, 1080),  -- store id 2
       ('Normal Store2', 'NORMAL', 1, true, 0, 0) -- store id 3
;

-- STORE FAVORITE
INSERT INTO favorite_stores(user_id, store_id)
VALUES (1, 1), -- favorite store id 1
       (1, 2) -- favorite store id 2
;

-- ORDER
INSERT INTO orders(user_id, store_id, order_state, cancel_message, canceled_at, completed_at, created_at)
VALUES --orders for store1
       (1, 1, 'NEW', null, null, null, '2021-03-01 09:00:00'),                                  -- order id 1
       (1, 1, 'CANCEL', 'expensive price', '2021-03-01 12:30:00', null, '2021-03-01 12:00:00'), -- order id 2
       (1, 1, 'COMPLETE', null, null, '2021-03-03 12:30:00', '2021-03-01 10:30:00'),            -- order id 3
       --orders for store3
       (1, 3, 'NEW', null, null, null, '2021-03-02 11:00:00'),                                  -- order id 4
       (1, 3, 'COMPLETE', null, null, '2021-03-03 12:30:00', '2021-03-03 09:00:00') -- order id 5
;

-- ORDER ITEM
INSERT INTO order_items(order_id, name, unit_price, unit_count)
VALUES --items for order1
       (1, 'MENU-A', 10000, 2), -- order item id 1
       (1, 'MENU-B', 5000, 3),  -- order item id 2
       --items for order2
       (2, 'MENU-A', 10000, 1), -- order item id 3
       --items for order3
       (3, 'MENU-C', 15000, 1), -- order item id 4
       (3, 'MENU-D', 20000, 1), -- order item id 5
       (3, 'MENU-E', 5000, 2),  -- order item id 6
       --items for order4
       (4, 'MENU-F', 30000, 2), -- order item id 7
       --items for order5
       (5, 'MENU-G', 10000, 2) -- order item id 8
;