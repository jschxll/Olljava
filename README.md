# Olljava

Olljava is a minimal and easy to use Java library for the Ollama API.

## How to use it

- [Generating a completion](#generating-a-completion)
- [Generating a chat completion](#generating-a-chat-completion)
- [Interacting with Ollama utilities](#interacting-with-ollama-utilities)
- [Create custom models](#create-custom-models)
- [Classes and Interfaces](#classes-and-interfaces)

### Generating a completion

There are two ways of receiving a response from a given model:

1. Streaming the response
2. Receiving the response as one JSON element

Here is a simple example given, which shows how a simple completion can be implemented:

````
Ollama host = new Ollama(new URL("http://localhost:11434"));
OllamaCompletion completion = new OllamaCompletion.Builder(host, new StreamHandler())
    .setModel("llama3")
    .setPrompt("Why's the sky blue?");
completion.execute();
````

If you want to include other options like images or modelfile modifications, you can take a
look [here](https://github.com/ollama/ollama/blob/main/docs/api.md#generate-a-completion) in the official Ollama docs,
to find the fitting options you want to add. **All options that there are listed, are also in the library implemented.**

Without streaming the same example looks like this:

````
Ollama host = new Ollama("http://localhost:11434");
OllamaCompletion completion = new OllamaCompletion.Builder(host)
    .setModel("llama3")
    .setPrompt("Why's the sky blue?")
    .setStreaming(false);
OllamaResponse resp = completion.execute().getResponse();
System.out.println(resp.getResponse());
````

**Attention**: Streaming is the preconfigured option if you don't specify anything else. You can deactivate it
with ``setStreaming(false)`` in your ChatCompletion or OllamaCompletion builder class.

### Generating a chat completion

Another option to receive a response from the Ollama API is via a chat completion. It differentiates itself from a '
normal completion, since it allows a complete chat history in the sent JSON, so the user can mention and access
requests and responses from previous conversations.
Such a request can look like the following:

````
Ollama host = new Ollama("http://localhost:11434");
List<Message> messages = List.of(new Message(ChatRoles.USER, "Why's the sky blue?"),
    new Message(ChatRoles.ASSISTANT, "[ANSWER]"),
    new Message(ChatRoles.USER, "Okay, but can you explain me the phenomena in simpler terms?"));

ChatCompletion completion = new ChatCompletion.Builder(host)
    .setModel("phi3")
    .setStreaming(false)
    .setMessages(messages)
    .build();
ChatResponse response = completion.execute().getResponse();
System.out.println(response.getContent());
````

Additionally, it's possible to add images to a Message, as well as a system prompt. Each Message need a specific role,
like "user", "assistant" and "system", in order to let the LLM know, how the previous chat looked like and where to
answer prompts.

### Interacting with Ollama utilities

**List local models:**

````
Ollama host = new Ollama("http://localhost:11434");
List<Model> models = host.getModels();
````

**List running models:**

````
Ollama host = new Ollama("http://localhost:11434");
List<Model> models = host.getRunningModels();
````

**Pull models (optional streaming is supported)**

````
Ollama host = new Ollama("http://localhost:11434");
host.pullModel("phi3", new StreamHandler());
````

**Push models (optional streaming is supported):**

````
Ollama host = new Ollama("http://localhost:11434");
host.pushModel("[NAME]", new StreamHandler());
````

If you want to get only one status JSON, use the same pullModel() method, without calling a StreamHandler.

**Copy models**

````
Ollama host = new Ollama("http://localhost:11434");
host.copyModel("phi3", "phi3_copy");
````

The first parameter specifies the model, which should be copied and the second parameter the name of the copy.

**Delete models**

````
Ollama host = new Ollama("http://localhost:11434");
host.deleteModel("phi3_copy");
````

**Generate embeddings from a model**

````
Ollama host = new Ollama("http://localhost:11434");
PayloadBuilder builder = new PayloadBuilder()
    .setModel("phi3")
    .setPrompt("The sky is blue because ...");
List<Float> embeddings = host.generateEmbeddings(builder.build().toJson());
System.out.println(embeddings);
````

### Create custom models

If you want to build your own model with a custom modelfile, you create a Modelfile, in which you write the modified
parameters. You can find
help [here](https://github.com/ollama/ollama/blob/main/docs/modelfile.md#valid-parameters-and-values), if you don't know
where to start. <br>
After you finished your Modelfile, you can pass it as an argument to the ModelFileReader class like so:

````
Ollama host = new Ollama("http://localhost:11434");
ModelFileReader reader = new ModelFileReader("[PATH TO YOUR MODELFILE]");
host.createModel("[NAME]", reader.getModelFileContent());
````

If you want to stream the response you can do modify the code like this:

````
Ollama host = new Ollama("http://localhost:11434");
ModelFileReader reader = new ModelFileReader("[PATH TO YOUR MODELFILE]");
host.createModel("[NAME]", reader.getModelFileContent(), new StreamHandler());
````

### Classes and Interfaces

| Class/Interface name    | Class Definition                                                                                                                                                                                                                                                     | Methods                                                                                                                                                                                                                                                                                         |
|-------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Completion.java         | An abstract class which the structure and methods of the children ChatCompletion and OllamaCompletion. Additionally, it contains the parent Builder class for the children with pre-implemented methods defining Modelfile functions to a chat or ollama completion. | **execute()**<br/> Executes the actual request to the Ollama API in a new Thread. <br/><br/> **getResponse()**<br/> Returns a fitting response class like OllamaResponse or ChatResponse. <br/><br/> **getJson()** <br/> Returns the final JSON from the Builder class of the Completion child. |
| Completion.Builder.java | A nested Java class in the Completion class containing Modelfile-like methods to manipulate the build-in Modelfile of the model where the request is sent to.                                                                                                        | You find [here](https://github.com/ollama/ollama/blob/main/docs/modelfile.md#valid-parameters-and-values) the definitions of each parameter.                                                                                                                                                    |
| Response.java           | Contains mappings for JSON elements which are both in a generated and chat completion. Children of the class are OllamaResponse and ChatResponse which have additionally mappings for their unique response.                                                         | getCreationDate() <br/> isDone() <br/> getTotalDuration() <br/> getLoadDuration() <br/> getPromptEvalCount() <br/> getPromptEvalDuration() </br> getEvalCount() <br/> getEvalDuration()                                                                                                         |
| Model.java              | A Java representation of Ollama models with their properties.                                                                                                                                                                                                        | getName() <br/> getModel() <br/> getSize() <br/> getDigest() <br/> getDetails(): <br/> Returns a Model.Details object. <br/> getModificationDate() </br> getExpireDate() </br> getVramSize()                                                                                                    |
| Model.Details.java      | Nested Java class in the Model class representing more details of a model.                                                                                                                                                                                           | getFormat() </br> getParentModel() </br> getFamily() <br/> getParameterSize() </br> getQuantizationLevel() </br> getFamilies()                                                                                                                                                                  |
| JsonStreamHandler.java  | An Interface which handles the incoming Json elements of a streaming request. With it you can build your own StreamHandler, like the one implemented called StreamHandler. It extends of the built-in Consumer interface.                                            | accept(String s)                                                                                                                                                                                                                                                                                |
| ChatRoles.java          | Contains chat roles in order to build chat histories.                                                                                                                                                                                                                | USER, ASSISTANT, SYSTEM                                                                                                                                                                                                                                                                         |
| Message.java            | Represents a message element in the JSON for a chat completion. With it you can build chat histories containing a role, a content String and optionally a list of pictures.                                                                                          | getRole() </br> setRole(String role) </br> getContent() </br> setContent(String content) </br> setImages(List<File> images) </br> hasImages()                                                                                                                                                   |

#### You want more details and options? Visit the [official Ollama docs](https://github.com/ollama/ollama/blob/main/docs/api.md).
**INFO**<br/>
This project uses the [Jackson library](https://github.com/FasterXML/jackson) licensed under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0.html) and contributed by FasterXML.