import SignInForm from '@/components/Forms/SignInForm';
import '../sections_style.css';

const SingInSection = () => {
    return(
        <>
        <section className="section sign-in-section">
            <div className="container">
                <SignInForm/>
            </div>
        </section>
        </>
    );
};

export default SingInSection;
