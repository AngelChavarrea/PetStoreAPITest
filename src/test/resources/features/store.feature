Feature: Validar la creación y consulta de órdenes en el API de Store

  Background:
    Given que el sistema está conectado a la base de datos
    And que el API de Store está disponible

  Scenario Outline: Creacion de una orden en la tienda
    When envío una solicitud POST a "/store/order" con los siguientes datos:
      | id     | petId | quantity | shipDate     | status   | complete |
      | <id>   | <petId> | <quantity> | <shipDate> | <status> | <complete> |
    Then el código de respuesta debe ser 200
    And el cuerpo del response debe contener el ID de la orden <id>

  Examples:
    | id  | petId | quantity | shipDate         | status   | complete |
    | 123 | 456   | 2        | 2024-09-01T12:00:00Z | approved | true  |

  Scenario Outline: Consulta de una orden en la tienda
    Given que existe una orden con ID <id>
    When envio una solicitud GET a "/store/order/<id>"
    Then el código de respuesta debe ser 200
    And el cuerpo del response debe contener los datos de la orden:
      | id      | <id>       |
      | petId   | <petId>    |
      | quantity| <quantity> |
      | status  | <status>   |
      | complete| <complete> |

  Examples:
    | id  | petId | quantity | status   | complete |
    | 123 | 456   | 2        | approved | true  |
