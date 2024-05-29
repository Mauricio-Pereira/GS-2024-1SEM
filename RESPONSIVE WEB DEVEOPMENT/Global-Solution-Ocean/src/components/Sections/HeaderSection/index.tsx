import Link from "next/link";
import '../sections_style.css';

const HeaderSection = () => {
    return(
        <>
        <section className="section header-section">
            <div className="container">
                <div className="title">
                    <span>Blue CommerceTech</span>
                    <h1>Blue Horizon</h1>
                    <span>Onde o oceano de sustentabilidade encontra as ondas do comércio</span>
                    <div className="buttons">
                        <Link href="/sign-in-page">Cadastre-se</Link>
                        <Link href="/login-page">Login</Link>
                    </div>
                </div>
                <div className="logo-img">
                    <img src="/images/logo.png" alt="" />
                </div>
            </div>
        </section>
        </>
    );
};

export default HeaderSection;