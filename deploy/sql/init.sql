CREATE TABLE files{
    id SERIAL PRIMARY KEY,
    record_id INT,
    parent_id INT,
    name TEXT
};

INSERT INTO files (table_id, parent_id, name) VALUES
(1, null, 'Документы'),
(2, null, 'Загрузки'),
(3, null, 'Рабочий стол'),
(4, null, 'Музыка'),
(5, null, 'Картинки'),
(6, null, 'Видео'),
(7, null, 'Корзина'),
(8, 1, 'Основное'),
(9, 1, 'Архив'),
(10, 8, '0102'),
(11, 9, '0202');