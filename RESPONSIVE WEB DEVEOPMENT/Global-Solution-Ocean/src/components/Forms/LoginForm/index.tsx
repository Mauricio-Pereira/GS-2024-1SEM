'use client'

import axios from 'axios';
import { useState } from 'react';
import '../forms_style.css';

const LoginForm = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState(''); // Estado para armazenar a mensagem de erro

    const handleInputChange = (event: any) => {
        switch (event.target.name) {
            case 'email':
                setEmail(event.target.value);
                break;
            case 'password':
                setPassword(event.target.value);
                break;
            default:
                break;
        }
    };

    const handleSubmit = async (event: any) => {
        event.preventDefault(); // Previne o comportamento padrão de recarga da página

        try {
            const response = await axios.post('InformeOCaminhoAqui', {
                email: email,
                password: password,
            }, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            console.log('Login realizado com sucesso:', response.data);
            localStorage.setItem('clienteEmail', email); // Armazena o email no localStorage
            window.location.href = '/products'; // Redireciona para outra página após o login bem-sucedido
        } catch (error) {
            if (axios.isAxiosError(error)) {
                console.error('Erro ao realizar login:', error.response?.data || error.message);
                setErrorMessage(error.response?.data || 'Erro ao realizar login.');
            } else {
                console.error('Erro desconhecido ao realizar login:', error);
                setErrorMessage('Erro desconhecido ao realizar login.');
            }
        }
    };

    return (
        <div className='form login-form'>
            <form onSubmit={handleSubmit}>
                <div className='email-box'>
                    <label htmlFor='email'>Email do Usuário</label>
                    <input className="input" type="text" id="email" name="email" placeholder="email@email.com" value={email} onChange={handleInputChange} />
                </div>
                
                <div className='password-box'>
                    <label htmlFor='password'>Senha</label>
                    <input className="input" type="password" id="password" name="password" placeholder="Digite sua senha aqui" value={password} onChange={handleInputChange} />
                </div>

                <input type="submit" className="submit-btn" value="Login" />

                {errorMessage && <p className="error-message">{errorMessage}</p>} {/* Exibe a mensagem de erro */}
            </form>
        </div>
    );
}

export default LoginForm;
