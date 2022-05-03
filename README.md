## Файловый менеджер

Для просмотра файлов на хостовой машине, укажите нужные каталоги в блоке **volumes** файла **docker-compose.yml**. 

> Внимание!  
> По умолчанию доступен каталог **/backend** из текущего проекта хостовой машины,
доступ к нему можно получить через каталог **/mnt/host** докер образа. 

Для запуска выполните команду:
```shell
docker-compose.yml up
```

Интерфейс будет доступен по адресу: http://localhost:3000
