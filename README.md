# Spring VK API

Этот проект представляет собой Spring Boot приложение для работы с VK API. Приложение поддерживает метрики Prometheus и логирование в Logstash.

## Требования

- Java 21 (для локальной сборки)
- Maven 3.6+ (для локальной сборки)
- Docker
- VK API токен и группа

## Установка

1. Клонируйте репозиторий:
    ```sh
    git clone https://github.com/daniilvohromeev/Spring.Kotlin.vkapi.git
    cd Spring.Kotlin.vkapi
    ```

2. Установите необходимые переменные окружения:
    ```sh
    export VK_TOKEN=your_vk_token
    export VK_GROUP_ID=your_vk_group_id
    export LOGSTASH_HOST=logstash
    export LOGSTASH_PORT=5044
    export VK_SECRET=your_vk_secret
    export VK_API_VERSION=your_vk_api_version
    ```

3. Соберите и запустите приложение:
    ```sh
    docker-compose up --build
    ```
