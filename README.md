# CURP and RFC API

## Introduction

This API provides functionality for generating CURP (Clave Única de Registro de Población) and RFC (Registro Federal de Contribuyentes) codes based on input data. The API takes a JSON payload with specific information and returns the generated CURP and RFC codes as the response.

Please note that the algorithm used to generate the last 2 digits of CURP and the last 3 digits of RFC contains hidden logic, making it impossible to achieve precision.

## Input

The API expects a POST request to the '/api/generarCurp' endpoint with the following JSON payload structure:

{
"nombre": "Alexis",
"apellidoPaterno": "Gutierrez",
"apellidoMaterno": "Oseguera",
"fechaNacimiento": "1995-06-08",
"genero": "H",
"estado": "MC"
}

The input fields are defined as follows:

- `nombre`: First name of the person.
- `apellidoPaterno`: Paternal last name of the person.
- `apellidoMaterno`: Maternal last name of the person.
- `fechaNacimiento`: Date of birth of the person in the format 'yyyy-MM-dd'.
- `genero`: Gender of the person ('H' for male or 'M' for female).
- `estado`: Code representing the state where the person was born.

## Output

Upon receiving a valid request, the API generates the CURP and RFC codes based on the input data and returns the response as a JSON object.

The response structure will be as follows:

{
"curp": "Generated CURP code",
"rfc": "Generated RFC code"
}

                                  
Please note that the last 2 digits of the CURP code and the last 3 digits of the RFC code are generated using an algorithm with hidden logic. As a result, achieving precision in these digits is not possible.

## Getting Started

To use this API, follow these steps:

1. Clone the repository to your local machine.
2. Install the required dependencies.
3. Build and run the application.
4. Send a POST request to the '/api/generarCurp' endpoint with the required input data in the JSON payload.
5. Receive the generated CURP and RFC codes in the API response.

## Conclusion

The CURP and RFC API provides a simple way to generate CURP and RFC codes based on input data. However, please note that the precision of the last digits of the CURP and RFC codes cannot be achieved due to the hidden logic in the algorithm used for their generation.

For more information and support, please refer to the documentation or contact the API maintainers.

