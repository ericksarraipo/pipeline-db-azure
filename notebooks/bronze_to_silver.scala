// Databricks notebook source
// MAGIC %md
// MAGIC ###Transformando os dados para a camada silver

// COMMAND ----------

// MAGIC %python
// MAGIC dbutils.fs.ls("/mnt/dados/bronze")
// MAGIC

// COMMAND ----------

// MAGIC %md
// MAGIC ###Lendo os dados na camada bronze

// COMMAND ----------

val path = "dbfs:/mnt/dados/bronze/dataset_imoveis/"
val df = spark.read.format("delta").load(path)


// COMMAND ----------

display(df)


// COMMAND ----------

// MAGIC %md
// MAGIC ###Transformando os campos do json em coluna

// COMMAND ----------

display(df.select("anuncio.*"))


// COMMAND ----------

display(df.select("anuncio.*", "anuncio.endereco.*"))



// COMMAND ----------

val dados_detalhados = df.select("anuncio.*", "anuncio.endereco.*")

// COMMAND ----------

// MAGIC %md
// MAGIC ###Removendo colunas

// COMMAND ----------

val df_silver = dados_detalhados.drop("caracteristicas", "endereco")
display(df_silver)


// COMMAND ----------

// MAGIC %md
// MAGIC ###Salvando na camada silver
// MAGIC

// COMMAND ----------

val path = "dbfs:/mnt/dados/silver/dataset_imoveis"
df_silver.write.format("delta").mode("overwrite").save(path)


// COMMAND ----------


