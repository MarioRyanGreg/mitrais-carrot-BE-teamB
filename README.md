# Mitrais Carrot

## Installation

### Clone repo

``` bash
# clone the repo
$ https://git-codecommit.ap-southeast-1.amazonaws.com/v1/repos/mitrais-carrot-BE-teamB

# go into app's directory
$ cd mitrais-carrot-BE-teamB
```

### create mitrais_carrot database and change the database connection in 
``` bash
copy `src/main/resources/application.properties.example` and rename into `src/main/resources/application.properties`
now change with your db config connection
```

### Run Application
``` bash
# install app's dependencies
$ mvn spring-boot:run

# Open browser
http://localhost:8989/
```

### Using JWT Authentication

if you are not registered yet, please register your user and password. 

``` bash
# Endpoint http://localhost:8989/api/v1/signup
# Example Request Body
{
	"name": "Mimin",
	"username": "mimin",
	"email": "mimin@mitrais.com",
	"password": "mitrais"
}
```

if you are already registered, please login using this

``` bash
# Endpoint http://localhost:8989/api/v1/signin
# Example Request Body
{
	"usernameOrEmail": "mimin", // using username or email
	"password": "mitrais"
}

# Example Response
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNTM3MDg5NDE5fQ.FmKAVMHeL_ULGstaEPfjeuadRZjvpcitxgF8qUezsBekYvWYwFRGo0ex5IGl7KyDTiOddeeMK8C_VpGo1eJp2Q",
    "tokenType": "Bearer"
}
```

### View Swagger API Doc
``` bash
# view the swagger json
http://localhost:8989/v2/api-docs
# view the swagger UI
http://localhost:8989/swagger-ui.html
```
