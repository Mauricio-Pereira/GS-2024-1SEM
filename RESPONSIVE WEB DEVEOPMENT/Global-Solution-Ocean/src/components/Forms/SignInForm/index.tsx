'use client'

import Link from 'next/link';
import axios from "axios";
import { useState } from "react";
import "../forms_style.css"


const SignInForm: React.FC = () => {
  // Estados para cada campo do formulário
  const [formData, setFormData] = useState({
    name: '',
    lastName: '',
    email: '',
    password: '',
    birthDate: '',
    phone: '',
    userType: '',
    zipCode: '',
    street: '',
    neighborhood: '',
    city: '',
    state: '',
    country: '',
    number: '',
    complement: ''
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

  const validatePassword = (password: string) => {
    const hasUpperCase = /(?=.*[A-Z])/;
    const hasLowerCase = /(?=.*[a-z])/;
    const hasSpecialChar = /(?=.*[@$#%^&+=])/;
    const minLength = /.{9,}/;
  
    if (!hasUpperCase.test(password) ||!hasLowerCase.test(password) ||!hasSpecialChar.test(password) ||!minLength.test(password)) {
      return false; // Retorna falso se alguma condição não for atendida
    }
  
    return true; // Retorna verdadeiro se todas as condições forem atendidas
  };

  const handleSubmit = async (event: any) => {
    event.preventDefault();

    if (!validatePassword(formData.password)) {
      setErrorMessage("A senha deve ter pelo menos 9 dígitos, incluindo uma letra maiúscula, uma letra minúscula e um caractere especial.");
      return; // Interrompe a execução se a senha não passar na validação
    }
    
    try {
      const response = await axios.post(`http://localhost:8082/bluehorizon/users`, formData,{
        headers: {
          'Content-Type': 'application/json',
        },
      });
      console.log('Cliente cadastrado com sucesso:', response.data);
      setSuccessMessage('Cliente cadastrado com sucesso!');
      window.location.href = '/login-page';
      setErrorMessage('');
      setFormData({
        name: '',
        lastName: '',
        email: '',
        password: '',
        birthDate: '',
        phone: '',
        userType: '',
        zipCode: '',
        street: '',
        neighborhood: '',
        city: '',
        state: '',
        country: '',
        number: '',
        complement: ''
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
            <p>Informe suas informações de usuário</p>

            <label htmlFor="name">Nome e Sobrenome</label>
            <div className="name-box">
              <input type="text" name="name" id="name" placeholder="Nome" value={formData.name} onChange={handleChange} />
              <input type="text" name="lastName" placeholder="Sobrenome" value={formData.lastName} onChange={handleChange} />
            </div>

            <label htmlFor="date">Data de Nasimento</label>
            <div className="date-box">
              <input type="date" name="birthDate" id="date" placeholder="Data de Nascimento" value={formData.birthDate} onChange={handleChange}/>
            </div>

            <label htmlFor="email">Informações de Contato</label>
            <div className="contact-box">
              <input type="email" name="email" id="email" placeholder="Email" value={formData.email} onChange={handleChange} />
              <input type="phone" name="phone" placeholder="Telefone" value={formData.phone} onChange={handleChange}/>
            </div>

            <label htmlFor="userType">Tipo de usuário</label>
            <div className="usertype-box">
              <select name="userType" id="userType" value={formData.userType} onChange={handleChange}>
                <option>*Selecione o tipo de usuário*</option>
                <option value="buyer">Comprador</option>
                <option value="admin_company">Administrador de Empresa</option>
                <option value="admin_ngo">Administrador de ONG</option>
              </select>
            </div>
            
            <label htmlFor="password">Senha</label>
            <div className="password-box">
              <input type="password" name="password" id="password" placeholder="Senha" value={formData.password} onChange={handleChange} />
            </div>
          </div>

          <div className="address-boxes">
            <p>Informações de Endereço</p>
            
            <div className="address-box">
              <input type="text" name="zipCode" placeholder="CEP" value={formData.zipCode} onChange={handleChange} />
              <input type="text" name="country" placeholder="País" value={formData.country} onChange={handleChange} />
              <input type="text" name="state" placeholder="Estado" value={formData.state} onChange={handleChange} />
              <input type="text" name="city" placeholder="Cidade" value={formData.city} onChange={handleChange} />
              <input type="text" name="neighborhood" placeholder="Bairro" value={formData.neighborhood} onChange={handleChange} />
              <input type="text" name="street" placeholder="Rua" value={formData.street} onChange={handleChange} />
              <input type="text" name="number" placeholder="Número" value={formData.number} onChange={handleChange} />
              <input type="text" name="complement" placeholder="Complemento" value={formData.complement} onChange={handleChange} />
            </div>
          </div>

          <input type="submit" className="submit-btn" value="Cadastrar Usuário" />

          <span>Já possui cadastro? <Link href="/login-page">Click aqui</Link></span>

          {errorMessage && <p className="error-message">{errorMessage}</p>}
          {successMessage && <p className="success-message">{successMessage}</p>}
        </form>
    </div>
    </>
  );
}

export default SignInForm;