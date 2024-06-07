import SignInForm from '@/components/Forms/SignInForm';
import '../sections_style.css';
import './signInSection_style.css';

const SingInSection = () => {
    return(
        <>
        <section className="section sign-in-section">
            <div className="container">
                <h1>Cadastratre-se já</h1>
                <SignInForm/>
            </div>
        </section>
        </>
    );
};

export default SingInSection;
