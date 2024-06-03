'use client'

import axios from "axios";
import { useState } from "react";
import "../forms_style.css"


const SignInForm: React.FC = () => {
  // Estados para cada campo do formulário
  const [formData, setFormData] = useState({
    nome: '',
    sobrenome: '',
    email: '',
    password: '',
    dataNascimento: '',
    phone: '',
    usertype: '',
    cep: '',
    rua: '',
    bairro: '',
    cidade: '',
    estado: '',
    pais: '',
    numero: '',
    complemento: ''
  });

  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');

  const handleChange = (event: any) => {
    const { name, value } = event.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleSubmit = async (event: any) => {
    event.preventDefault();

    // Formatar data de nascimento no formato dd/MM/yyyy
    const formattedData = {
      ...formData,
      dataNascimento: new Date(formData.dataNascimento).toLocaleDateString('pt-BR', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
      })
    };

    console.log('Data de nascimento enviada:', formattedData.dataNascimento); // Log para verificar a data

    try {
      const response = await axios.post(`InformeOCaminhoAqui`, {
        headers: {
          'Content-Type': 'application/json',
        },
      });
      console.log('Cliente cadastrado com sucesso:', response.data);
      setSuccessMessage('Cliente cadastrado com sucesso!');
      setErrorMessage('');
      setFormData({
        nome: '',
        sobrenome: '',
        email: '',
        password: '',
        dataNascimento: '',
        phone: '',
        usertype: '',
        cep: '',
        rua: '',
        bairro: '',
        cidade: '',
        estado: '',
        pais: '',
        numero: '',
        complemento: ''
      });
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.error('Erro ao cadastrar cliente:', error.response?.data || error.message);
        setErrorMessage(error.response?.data || 'Erro ao cadastrar cliente.');
        setSuccessMessage('');
      } else {
        console.error('Erro desconhecido ao cadastrar cliente:', error);
        setErrorMessage('Erro desconhecido ao cadastrar cliente.');
        setSuccessMessage('');
      }
    }
  };

  return (
    <>
    <div className="form sign-in-form">
        <form onSubmit={handleSubmit}>
          <div className="user-boxes">
            <span>Informe suas informações de usuário</span>

            <div className="name-box">
              <input type="text" name="nome" placeholder="Nome" value={formData.nome} onChange={handleChange} />
              <input type="text" name="sobrenome" placeholder="Sobrenome" value={formData.sobrenome} onChange={handleChange} />
            </div>

            <div className="date-box">
              <label htmlFor="date">Data de Nasimento</label>
              <input type="date" name="dataNascimento" id="date" placeholder="Data de Nascimento" value={formData.dataNascimento} onChange={handleChange}/>
            </div>

            <div className="contact-box">
              <input type="email" name="email" placeholder="Email" value={formData.email} onChange={handleChange} />
              <input type="phone" name="phone" placeholder="Telefone" value={formData.phone} onChange={handleChange}/>
            </div>

            <div className="usertype-box">
              <input type="text" name="usertype" placeholder="Tipo de Usuário" value={formData.usertype} onChange={handleChange} />
              {/*
              <input type="ratio" name="usertype" value={formData.usertype} onChange={handleChange} />
              <input type="ratio" name="usertype" value={formData.usertype} onChange={handleChange} />
              <input type="ratio" name="usertype" value={formData.usertype} onChange={handleChange} />
              */}
            </div>
            
            <div className="password-box">
              <input type="password" name="senha" placeholder="Senha" value={formData.password} onChange={handleChange} />
            </div>
          </div>

          <div className="address-boxes">
            <span>Informações de Endereço</span>
            
            <div className="address-box">
              <input type="text" name="cep" placeholder="CEP" value={formData.cep} onChange={handleChange} />
            
              <input type="text" name="pais" placeholder="País" value={formData.pais} onChange={handleChange} />
              <input type="text" name="estado" placeholder="Estado" value={formData.estado} onChange={handleChange} />
              <input type="text" name="cidade" placeholder="Cidade" value={formData.cidade} onChange={handleChange} />
              <input type="text" name="bairro" placeholder="Bairro" value={formData.bairro} onChange={handleChange} />
              <input type="text" name="rua" placeholder="Rua" value={formData.rua} onChange={handleChange} />
              <input type="text" name="numero" placeholder="Número" value={formData.numero} onChange={handleChange} />
              <input type="text" name="complemento" placeholder="Complemento" value={formData.complemento} onChange={handleChange} />
            </div>
          </div>

          <input type="submit" className="submit-btn" value="Cadastrar Usuário" />

          {errorMessage && <p className="error-message">{errorMessage}</p>}
          {successMessage && <p className="success-message">{successMessage}</p>}
        </form>
    </div>
    </>
  );
}

export default SignInForm;