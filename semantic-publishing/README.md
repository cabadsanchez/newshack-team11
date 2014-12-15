# Java SDK for Textalytics  Semantic Publishing API

> Produce more valuable content, more quickly and with lower costs, and open up new ways of doing business.

## Description

This SDK allows to use easily the functionality provided by [Textalytics Semantic Publishing API](https://textalytics.com/api-text-analysis-semantic-publishing). 
It has the following structure:

* __/domain__: wrapper POJOs around the services inputs and outputs.
* __/manage__: helper classes for making requests to the _**User Resources Management**_ service.
* __/utils__: helper classes for setting requests parameters.
* __/examples__: contains a number of classes with examples on how to use the different services.  
* __SempubClient.java__: wrapper around _**Semantic Publishing API**_ service operations. 
* __SempubResponseHandler.java__: transforms responses into wrapper POJOs in the domain package.
* __SempubSSLSocketFactory__: SocketFactory that accepts self-signed certificates. Use default implementation if you include textalytics.com in your keystore.
* __TextalyticsException__: Encapsulates _**Semantic Publishing API**_ errors.
* __TextalyticsClientException__: Encapsulates _**Semantic Publishing sdk**_ errors.
* __Response.java__, __Status.java__, __Result.java__: wrappers POJOS around the API response.

## Example usage

The project uses Maven 2 to build:


You can import in an IDE like Eclipse by: 

1. Select File > Import... 
2. Search for Maven > Existing Maven project

You may have to include your **API License** key in the /src/main/resources/config.properties file in order to 
use the API.

The folder _examples_ contains several scripts with different use cases of the different services
[Semantic Publishing](https://textalytics.com/api-text-analysis-semantic-publishing) provides. There are three 
services: __semantic_tagging__, to extract semantic information from a text, __check__, to proofread it, 
and __manage__, that allows to create and manage user-defined resources to use in the first two services.

You can run any of them by simply running the _main()_ method each one of the examples contain.

There are currently five example scripts available:

##### _ExtractSemanticInfo.java_ 
Extracts semantic information using the __semantic_tagging__ service and outputs a light version of 
the elements detected.
##### _ExtractSemanticInfoWithDictionary.java_
Creates a new user-defined dictionary, adds two entities and a concept using the __manage__ service and 
then extracts semantic information and outputs a light version of the elements detected. The text used is 
the same as in the class _ExtractSemanticInfo.java_ to better compare the advantages of defining user resources.

##### _ExtractSemanticInfoWithModel.java_
This script creates a new user-defined model, adds two categories using the __manage__ service, and then 
extracts semantic information and outputs a light version of the elements detected. The text used is the same 
as in the class _ExtractSemanticInfo.java_ to better compare the advantages of defining user resources.
##### _ProofreadText.java_
Proofreads the given text using the __check__ service and outputs a light version of the issues detected.
##### _ProofreadTextWithDictionary.java_
Creates a new user-defined dictionary, adds two entities and a concept using the __manage__ service and then 
proofreads it using the __check__ service and outputs a light version of the issues detected.

### Detailed list of dependencies
```xml
<dependency>
	<groupId>com.fasterxml.jackson.core</groupId>
	<artifactId>jackson-annotations</artifactId>
	<version>2.0.6</version>
</dependency>
```

```xml
<dependency>
	<groupId>com.fasterxml.jackson.core</groupId>
	<artifactId>jackson-core</artifactId>
	<version>2.0.6</version>
</dependency>
```

```xml
<dependency>
	<groupId>com.fasterxml.jackson.core</groupId>
	<artifactId>jackson-databind</artifactId>
  	<version>2.0.6</version>
</dependency>
```

```xml
<dependency>
	<groupId>org.apache.httpcomponents</groupId>
	<artifactId>httpclient</artifactId>
	<version>4.0.3</version>
</dependency>
```

```xml
<dependency>
	<groupId>log4j</groupId>
	<artifactId>log4j</artifactId>
	<version>1.2.16</version>
</dependency>
```

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>3.8.1</version>
    <scope>test</scope>
</dependency>
```

## Contact

Do you have any questions? Do you have any suggestions on how we can keep improving? Have you found a bug?
Contact us at support@textalytics.com or through our [Feedback section](https://textalytics.com/core/feedback).

##Usage, License and Copying

Textalytics is a cloud service provided by DAEDALUS. S.A.
 
This client may be used in the terms described in the LICENSE file.

For details please refer to: http://www.textalytics.com

This client uses Apache HttpComponents Client 4.x , Apache Log4j 1.2 and Jackson 2.x. 

This libraries are distributed under the [Apache License Version 2.0](http://www.apache.org/licenses/)


Copyright (c) 2014, DAEDALUS S.A. All rights reserved
