// src/components/UserForm.js
import React, { useState } from 'react';
import axios from 'axios';
import './UserForm.css';

function UserForm() {
  const [submitted, setSubmitted] = useState({
    nome: '',
    email: '',
    senha: ''
  });
  const [cadastroSucesso, setCadastroSucesso] = useState(false);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setSubmitted({
      ...submitted,
      [name]: value
    });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await axios.post('http://localhost:8081/pessoa', submitted);
      console.log('Usuário criado:', response.data);
      setCadastroSucesso(true); // Define cadastroSucesso como true após o sucesso
      // Limpar o formulário após o sucesso, se necessário
      setSubmitted({ nome: '', email: '', senha: '' });
    } catch (error) {
      console.error('Erro ao criar usuário:', error);
      // Tratar erros, exibir mensagem de erro, etc.
    }
  };

  return (
    <div className="user-form-container">
      <h2>Cadastrar Novo Usuário</h2>
      {cadastroSucesso && <p>Cadastrado com sucesso!</p>}
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="nome"
          value={submitted.nome}
          onChange={handleChange}
          placeholder="Nome"
          required
        />
        <input
          type="email"
          name="email"
          value={submitted.email}
          onChange={handleChange}
          placeholder="Email"
          required
        />
        <input
          type="password"
          name="senha"
          value={submitted.senha}
          onChange={handleChange}
          placeholder="Senha"
          required
        />
        <button type="submit">Enviar</button>
      </form>
    </div>
  );
}

export default UserForm;
