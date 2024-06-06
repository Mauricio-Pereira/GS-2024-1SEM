import LoginForm from '@/components/Forms/LoginForm';
import '../sections_style.css';

const LoginSection = () => {
    return(
        <>
        <section className="section login-section">
            <div className="container">
                <h1>Faça o seu Login</h1>
                <LoginForm/>
            </div>
        </section>
        </>
    );
};

export default LoginSection;
