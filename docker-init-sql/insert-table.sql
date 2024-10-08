insert into member values (1, 'spring0913', 'SELLER');
insert into member values (2, 'happycat', 'BUYER');

insert into category values (1, 'FOOD');
insert into category values (2, 'CLOTHING');
insert into category values (3, 'COSMETIC');
insert into category values (4, 'HOUSEWARES');

insert into product values (1, 1, 3, 'test', 10000, 'http://imagetest.com', 'description', current_timestamp, null);
insert into market values (1, 1, 1000, 100, 75, 100, 5, '2024-03-01 01:00:00', '2024-03-05 01:00:00', 'WAIT', current_timestamp, null);
insert into market values (2, 1, 1000, 100, 100, 100, 5, '2024-03-01 01:00:00', '2024-03-05 01:00:00', 'SOLD_OUT', current_timestamp, null);
insert into market values (3, 1, 1000, 100, 75, 100, 5, '2024-03-01 01:00:00', '2024-12-31 01:00:00', 'WAIT', current_timestamp, null);