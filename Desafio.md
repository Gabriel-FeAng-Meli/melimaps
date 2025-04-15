### **Desafio: Sistema Inteligente de Rotas e Recomendação de Transporte Urbano**

---

#### **🎯 Objetivo:**

Criar um sistema em Spring Boot que recomenda as melhores rotas de transporte urbano para um usuário, considerando tempo, custo, distância e acessibilidade.

---

#### **🧩 Requisitos funcionais:**

* Expor uma API REST que receba um ponto de origem e destino.  
* Retornar diferentes rotas com recomendações otimizadas (por tempo, custo, número de conexões).  
* Incluir suporte para múltiplos tipos de transporte: ônibus, metrô e bicicleta.  
* Suportar modo "eco", que prioriza trajetos com menos emissões de CO2.  
* Incluir sugestões baseadas em rotas populares entre usuários semelhantes.

---

#### **🧠 Requisitos técnicos:**

##### **Estruturas e algoritmos:**

* Utilizar **grafo direcionado ponderado** para modelar a malha de transporte.  
* Utilizar **Dijkstra** ou **A\*** para calcular rotas otimizadas.  
* Utilizar **hashing** para mapear rapidamente pontos da cidade e armazenar dados de rotas.  
* Criar uma **árvore de decisão** para sugerir o melhor meio de transporte com base no perfil do usuário (ex: tempo disponível, orçamento, etc).  
* Armazenar as rotas mais usadas por usuário em uma **tabela hash com contagem de frequência**.

##### **Design Patterns:**

* **Strategy Pattern**: para alternar entre algoritmos de otimização (tempo, custo, distância).  
* **Factory Pattern**: para instanciar o algoritmo de rota com base no parâmetro passado.  
* **Template Method**: para padronizar o processo de geração de rotas com variações de transporte.  
* **Observer Pattern**: para notificar usuários quando uma rota favorita tiver alteração (simulado).  
* **Builder Pattern**: para construção de objetos complexos de resposta (ex: uma rota com várias paradas, tipos de transporte, custo total, etc).  
* **Decorator Pattern**: para adicionar funcionalidades às rotas (ex: acessibilidade, modo eco, preferências do usuário).  
* **Flyweight Pattern**: para otimizar o uso de memória com nós e arestas repetidos no grafo.

##### **Spring / Java:**

* API RESTful com Spring Boot.  
* Injeção de dependência com Spring.  
* Cache com `@Cacheable` para otimizar rotas já calculadas.  
* Spring Profiles como **feature toggles** para ativar/desativar tipos de transporte ou estratégias de rota.  
* Testes com `@WebMvcTest`, `@MockBean` e testes unitários para os algoritmos.

