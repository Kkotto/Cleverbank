# Overview

����������� ���������� ���������� ��� Clever-Bank.

## ����

- Java 17
- Gradle
- PostgreSQL
- JDBC
- Lombok
- log4j2

**_���������_** � ������� �� �������:

- Spring
- Hibernate

## ���� ������

�������� ��������:

- ���� (>=5)
- ������������ (>=20)
- ���� (>=40)
- ����������

� ������������� ����� ���� ����� � ������ ������.

## �����������

1. ����� ������ �������� ���������� ������� application.properties � ���������� resources:
   ``src/main/resources/application.properties``
2. application.properties ������ ��������� ��������� ��������:
    - jdbc.driver.name
    - jdbc.url
    - jdbc.user
    - jdbc.password
    - jdbc.database

������ ����������: [application.properties](example%2Fapplication.properties). ������ ���� ����� � �������������.

```
jdbc.driver.name=org.postgresql.Driver
jdbc.url=jdbc:postgresql://localhost:5432/
jdbc.user=postgres
jdbc.password=postgresql
jdbc.database=clever_bank
```

3. ���� ������ � ��������� � application.properties ������ ���������
   �������������. [������� � Java-������������ ���� ������.](src%2Fmain%2Fjava%2Fcom%2Fkkotto%2Fcleverbank%2Fconfig%2FDatabaseConfiguration.java)
4. �������, ��������� � [�������](#����-������), ���������
   �������������. [������� � Java-������������ ������.](src%2Fmain%2Fjava%2Fcom%2Fkkotto%2Fcleverbank%2Fconfig%2FTablesConfiguration.java)