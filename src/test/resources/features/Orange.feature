@PIM
Feature: Busqueda y filtros de la lista de empleados

  Background:
    Given que se abre el navegador y se navega a "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login"
    And que el usuario inicia sesión en OrangeHRM con el usuario "Admin" y la contraseña "admin123"
    And que el usuario ingresa al módulo PIM

  # Buscar un empleado por nombre
  @TC-001
  Scenario Outline: Buscar empleado por nombre
    Given el usuario ingresa "<Nombre>" en el campo Nombre del empleado de PIM
    And hace clic en Buscar
    Then se deben mostrar los resultados de la búsqueda
    And cada resultado debe contener "<Apellido>"

    Examples:
      | Nombre | Apellido |
      | Aaron  | Castro   |

  # Buscar un empleado por ID
  @TC-002
  Scenario Outline: Search employee by employee id
    When the user enters employee id "<employeeId>"
    And clicks Search
    Then the system should return "<expectedRecords>" record(s)

    Examples:
      | employeeId | expectedRecords |
      | 0001       | 1               |
      | 999999     | 0               |

  # Buscar empleados por estado laboral
  @TC-003
  Scenario Outline: Search employees by employment status
    When the user selects employment status "<status>"
    And clicks Search
    Then every result should have employment status "<status>"

    Examples:
      | status              |
      | Freelance           |
      | Full-Time Contract  |
      | Full-Time Permanent |
      | Full-Time Probation |
      | Part-Time Contract  |

  # Buscar empleados según el filtro de inclusión
  @TC-004
  Scenario Outline: Search employees using include filter
    When the user selects include option "<includeOption>"
    And clicks Search
    Then results should belong to "<includeOption>"

    Examples:
      | includeOption          |
      | Current Employees Only |

  # Búsqueda avanzada con múltiples filtros y validación resiliente
  @TC-005
  Scenario Outline: Verify employee search with multiple filters
    When the user enters employee name "<employeeName>"
    And the user enters employee id for filter "<employeeId>"
    And the user selects employment status filter "<status>"
    And the user selects include option "<includeOption>"
    And the user selects job title filter "<jobTitle>"
    And the user selects sub unit filter "<subUnit>"
    And the user selects supervisor name "<supervisor>"
    And clicks Search
    Then every result should match all selected criteria

    Examples:
      | employeeName | employeeId | status                | includeOption              | jobTitle                 | subUnit      | supervisor     |
      | Amelia      | 0001      | Full-Time Permanent   | Current Employees Only     | Account Assistant        | Administration | John Smith     |
      | James       | 0002      | Full-Time Contract    | Current and Past Employees | Automation Engineer L000 | Engineering  | Peter Anderson |
      | Linda       | 0003      | Part-Time Contract    | Past Employees Only        | Automaton Tester        | Development  | Linda Brown    |