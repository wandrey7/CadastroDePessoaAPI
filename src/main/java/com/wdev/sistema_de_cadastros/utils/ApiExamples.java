package com.wdev.sistema_de_cadastros.utils;

public class ApiExamples {
    public static final String SUCCESS_CREATE_PERSON = """
            {
            	"message": "registration completed",
            	"data": {
            		"personId": "b2c7df19-1219-4125-a758-f311a9a0605f",
            		"name": "Pedro Wandrey 2",
            		"email": "pedrowa1@gmail.com",
            		"age": 26,
            		"height": 1.32,
            		"responses": null
            	}
            }
            """;

    public static final String ERROR_INVALID_DATA_PERSON = """
            {
              "name": "The name field is mandatory",
              "email": "The email field is mandatory",
              "age": "Age must be at least 18",
              "height": "The height must be in the format 'X,XX'"
            }
            """;


    public static final String RETURN_DATA_QUESTION = """
            {
                "data": [
                    {
                        "questionText": "Qual seu nome completo?",
                        "questionId": "6e9d2bae-49d2-4c69-b4eb-759c415a51d7"
                    }
                ]
            }
            """;
}
