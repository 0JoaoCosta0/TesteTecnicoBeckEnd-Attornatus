# TesteTecnicoBeckEnd-Attornatus

<H3>Foi usado o Postman para teste durante o desenvolvimento da API</H3>

<H3>Endpoints utilizados na API:</H3>

<p>http://localhost:8080/pessoas -/- Requisição GET & POST para listar ou criar as pessoas cadastradas.</p>
<p>http://localhost:8080/pessoas/{id} -/- Requisição GET & PUT para consultar ou editar uma pessoa cadastrada.</p>
<p>http://localhost:8080/pessoas/{id}/enderecos -/- Requisição GET & POST para listar ou criar um endereço para uma pessoa cadastrada.</p>
<p>http://localhost:8080/pessoas/{id}/endereco-principal/{enderecoId} -/- Requisição PUT para definir um endereço principal para uma pessoa cadastrada.</p>

<H3>Exemplos:</H3>

<H4>Para criar ou editar uma pessoa: </H4>

<p> { </p>
<p> "nome": "Lucas Ferreira", </p>
<p> "dataNascimento": "2000-07-23" </p>
<p> } </p>

<H4>Para criar ou definir um endereço principal: </H4>

<p> { </p>
<p> "logradouro": "Rua A", </p>
<p> "cep": "12345-678", </p>
<p> "numero": "10", </p>
<p> "cidade": "São Paulo" </p>
<p> } </p>
