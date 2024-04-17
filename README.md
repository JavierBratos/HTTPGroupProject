# HTTPGroupProject
Group Project HTTP made by Javier Bratos Monje, Inés Casado Vela, David García Gadea.

1. Develop a "client library" that allows HTTP requests to be made in a simple and clean way, without getting into implementation details. That is, to create a series of functions that can be invoked in a way similar to this:

   ```
   const response = myClientLib.request('GET', 'http://localhost/cats, headers: {'key': 123}, body: {}')
   ```

   To validate that this first step has been successfully completed, tools like [Beeceptor](https://beeceptor.com) can be very useful.

2. Define a "server library," analogous to the "client library." In this case, we need to achieve a suite of functions that allow us to abstract to a certain extent from the reception of requests. For example, something along the lines of:

   ```
   myServerLib.on('get', '/cats', {
       ...
   });
   ```

   This part can be easily validated with tools like [Insomnia](https://insomnia.rest), [Postman](https://www.postman.com) or simply [cURL](https://curl.se).

3. Implement the HTTP client as an interactive CLI that uses the library from the first step to be able to launch dynamic requests. Again, Beeceptor is an excellent ally for debugging.

4. Enable in our HTTP server, using our "server library," a first endpoint that statically returns an HTML file read from disk.

5. Implement the CRUD in a basic way by adding more endpoints and simple in-memory persistence

6. Address possible error cases