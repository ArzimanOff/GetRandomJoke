## Основной код проекта находится в папке <a href="https://github.com/ArzimanOff/GetRandomJoke/tree/main/app/src/main">main (здесь)</a> 

# Это приложение для генерации коротких анекдотов на английском языке используя API
#### Используемые технологии: Java, Retrofit, RxJava 3, Room, API (<a href="https://github.com/15Dkatz/official_joke_api?tab=readme-ov-file">15Dkatz/official_joke_api</a>)
#### Архитектура MVVM

<h5>По состоянию на 12.08.24</h5>
<h2>Версия приложения 1.0 для генерации шуток Get Random Joke</h2>


<h3>Функционал:</h3>

| | |
|--------|--------|
|Название приложения| Get Random Joke|
|Иконка приложения: |<img src="https://github.com/user-attachments/assets/d9dde914-39d0-4ed2-af68-338236326fca" alt="drawing" width="100"/>|
|Экран с одной сгенерированной шуткой: |<img src="https://github.com/user-attachments/assets/70b8d8dd-94c7-42f6-96bc-72cfc786ddf1" alt="drawing" width="150"/>|
|Экран со списком сгенерированных шуток: |<img src="https://github.com/user-attachments/assets/e3aab804-e310-4acf-acfe-7acbe78971cf" alt="drawing" width="150"/>|
|Можно настраивать генерацию <br> Один анекдот или список (введенное кол-во) <br> если генерация одного анекдота, <br> то можно осуществить поиск по id| <img src="https://github.com/user-attachments/assets/05de8d28-0532-419b-bd50-4d6f55fe9951" alt="drawing" width="100"/> <img src="https://github.com/user-attachments/assets/85a7bda1-094a-4af3-b7f9-cbf68fe00275" alt="drawing" width="100"/> <img src="https://github.com/user-attachments/assets/4166aa96-1dfa-4800-8fbd-3ea151ad87c6" alt="drawing" width="100"/>|
|При ошибке загрузки появляется соответствующая заглушка и уведомление|<img src="https://github.com/user-attachments/assets/b75dd007-e908-49e5-8bca-5ced0d84789b" alt="drawing" width="150"/>|
|Во время самой загрузки отображается прогресбар| <img src="https://github.com/user-attachments/assets/31f2687b-5003-4dbd-bdbe-973f26d1f5e6" alt="drawing" width="150"/>|
|Добавлен экран просмотра избранных анекдотов (НО МЕХАНИЗМ НЕ РЕАЛИЗОВАН)| <img src="https://github.com/user-attachments/assets/3fcdea12-2dc8-41ad-93f5-8e3e0079ea2e" alt="drawing" width="150"/>|

<h3>TODO:</h3>

| | 
|--------|
|Добавить возможность генерации по типу анекдота (в API выделены 4 типа: "general", "knock knock", "programming", "dad" )|
|Реализовать механизм добавления анекдота в избранные|
|Реализовать механизм добавления своего собственного анекдота в избранные|
|Поработать над UI элементами|
|Исправить найденные визуальные и логические ошибки|
