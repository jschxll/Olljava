# Olljava

Olljava is a minimal and easy to use Java library for the Ollama API.

## How to use it

- [Generating a completion](#generating-a-completion)
- [Interacting with Ollama utilities](#interacting-with-ollama-utilities)

### Generating a completion

There are two ways of receiving a response from a given model:

1. Streaming the response
2. Receiving the response as one JSON element

Here is an example how a request can look like, when streaming the response of an completion request:

````
Ollama host = new Ollama(new URL("http://localhost:11434"));
if (host.isReachable()) {
    PayloadBuilder builder = new PayloadBuilder()
        .setModel("llava")
        .setPrompt("What can you see on that picture?")
        .setImages(new File("test.jpg"));
    String json = builder.build().toJson();
    OllamaCompletion completion = new OllamaCompletion(json, host, new StreamHandler());
    completion.execute();
}
````

**Streaming is the preconfigured option if you don't specify anything else.**

Without streaming the same example looks like this:

````
Ollama host = new Ollama(new URL("http://localhost:11434"));
if (host.isReachable()) {
    PayloadBuilder builder = new PayloadBuilder()
        .setModel("llava")
        .setPrompt("What can you see on that picture?")
        .setImages(new File("test.jpg"))
        .setStream(false);
    String json = builder.build().toJson();
    OllamaCompletion completion = new OllamaCompletion(json, host);
    OllamaResponse = completion.execute().getResponse();
    System.out.println(completion.getResponse());
}
````

### Interacting with Ollama utilities

**List local models:**

````
Ollama host = new Ollama(new URL("http://localhost:11434"));
List<Model> models = host.getModels();
````

**Pull models (with streaming)**

````
Ollama host = new Ollama(new URL("http://localhost:11434"));
host.pullModel("phi3", new StreamHandler());
````

If you want to get only one status JSON, use the same pullModel() method, without calling a StreamHandler.

**Copy models**

````
Ollama host = new Ollama(new URL("http://localhost:11434"));
host.copyModel("phi3", "phi3_copy");
````

The first parameter specifies the model, which should be copied and the second parameter the name of the copy.

**Delete models**

````
Ollama host = new Ollama(new URL("http://localhost:11434"));
host.deleteModel("phi3_copy");
````

**Generate embeddings from a model**

````
Ollama host = new Ollama(new URL("http://localhost:11434"));
PayloadBuilder builder = new PayloadBuilder()
    .setModel("phi3")
    .setPrompt("The sky is blue because ...");
List<Float> embeddings = host.generateEmbeddings(builder.build().toJson());
System.out.println(embeddings);
````

#### You want more details and options? Visit the [official Ollama docs](https://github.com/ollama/ollama/blob/main/docs/api.md).