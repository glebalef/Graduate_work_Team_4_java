<h1>Содержание</h1>
<li><a href="#project"> О проекте </a></li>
<li><a href="#team"> Участники </a></li>
<li><a href="#app"> О приложени </a></li>
<li><a href="#startapp"> Запуск приложения </a></li>
<li><a href="#api"> API </a></li>


<h1 id="project"> О проекте </h1>

Это дипломный проект группы №4. Разработка данного приложения  в командной работе.

<h1 id="team" > Группа №4. Участники проекта </h1> 
<li>Фисенко Евгений</li>
<li>Луговой Евгений</li>
<li>Двойных Глеб</li>
<li>Николаева Анна</li>

<h1 id="app" > Как работает приложение </h1> 
Работа приложения заключается в том, что на определенном сайте будет доступна возможность у пользователя зарегистрироваться на сайте. После регистрации, пользователь сможет заполнять карточки товаров, добавлять к ним комментарии и загружать картинки. 

<h1 id="startapp" >Как запустить приложение  </h1> 

Первоначально требуется установить Docker.

Установить и распаковать образ из контейнера через команндную строку.

Клонировать репозиторий в виртуальную машину java:

<blockquote>https://github.com/glebalef/Graduate_work_Team_4_java.git</blockquote>

Запустить код в виртуальной машине java.

В командной строке запустить docker контейнер:

<blockquote> docker run --rm --name adsclientv16-instance -p3000:3000 adsclient:v16 </blockquote>


<h1 id="api"> REST API </h1>
В приожении с помощью API запросов, на сайте имеется возможность выполнить такие запросы как:

регистрация пользователя
http://localhost:3000/sign-up

авторизация пользователя
http://localhost:3000/sign-in


добавление объявления
http://localhost:3000/newAd

выгрузка всех объявлений
http://localhost:3000

<h1> Технологии в проекте </h1> 
Язык и окружение 
<li>Java 17</li>
<li>Spring Boot/Web/Admin</li> 
<li>Hibernate</li>
<li>PostgreSQL</li>
<li>Liquibase</li>

Тестирование  
<li>JUnit</li> 
<li>Mockito</li>


Дополнительные инструменты
<li>Docker</li>
<h1> Резюме проекта </h1> 
В результате работы над проектом были прописаны возможности:

Создание, редактирование, удаление объявления.

Добавление комментариев.

Получение информации об всех добавленных объявлениях.

Неаутентифицированный пользователь имеет право только получать список объявлений.

Аутентифицированный пользователь может получать список объявлений, получать одно объявление, создавать объявление, редактировать и удалять свое объявление, получать список комментариев, создавать комментарии, редактировать/удалять свои комментарии, а так же загрузить аватарку профиля.

Администратор может дополнительно к правам пользователя редактировать или удалять объявления и комментарии любых других пользователей.
 
<h1> Дополнительно о проекте</h1> 
В процессе работы над проектом была изучена работа совместно с фронтенд разработкой. Так как для конечного пользователя необходим интерфейс, с помощью которого пользователь будет взаимодействовать с приложением.
<h1>  </h1> 
<h1>  </h1> 
