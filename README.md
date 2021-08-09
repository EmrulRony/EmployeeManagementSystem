# Employee Management System

## Preprequsites

1. JDK 11
2. Mysql RDBMS

## API Documentation

#### Create new Employee

| Request method | URI                   |
| :------------- | --------------------- |
| POST           | {host:port}/employees |

**Sample Request Body**

```json
{
  "firstName": "Monica",
  "lastName": "Geller",
  "designation": "Programmer",
  "birthDate": "1994-01-31T00:00:00.000+00:00",
  "cellPhone": "01782343434",
  "email": "monica@gmail.com",
  "department": "SD6",
  "address": {
    "country": "BD",
    "city": "Dhaka",
    "area": "Uttara",
    "postalCode": 1211
  },
  "attendances": []
}
```

#### Update existing employee

| Request method | URI                                 |
| :------------- | ----------------------------------- |
| PUT            | {host:port}/employees/{employee_id} |

**Sample Request Body**

```json
{
  "firstName": "Monica",
  "lastName": "Geller",
  "designation": "Programmer",
  "birthDate": "1994-01-31T00:00:00.000+00:00",
  "cellPhone": "01782343434",
  "email": "monica@gmail.com",
  "department": "SD6",
  "address": {
    "id": 7,
    "country": "BD",
    "city": "Dhaka",
    "area": "Baridhara",
    "postalCode": 1211
  },
  "attendances": []
}
```

#### Find Employee By ID

| Request method | URI                                 |
| :------------- | ----------------------------------- |
| GET            | {host:port}/employees/{employee_id} |

#### List all Employees

| Request method | URI                   |
| :------------- | --------------------- |
| GET            | {host:port}/employees |

#### Delete Employee By ID

| Request method | URI                                 |
| :------------- | ----------------------------------- |
| DELETE         | {host:port}/employees/{employee_id} |

#### Set Employee Check-in & Check-out record

| Request method | URI                                            |
| :------------- | ---------------------------------------------- |
| POST           | {host:port}/employees/{employee_id}/attendance |

#### Get Employee attendance report

| Request method | URI                                            |
| :------------- | ---------------------------------------------- |
| GET            | {host:port}/employees/{employee_id}/attendance |

