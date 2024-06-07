import Image from "next/image";
import Link from "next/link";
import '../sections_style.css';
import './headerSection_style.css'

const HeaderSection = () => {
    return(
        <>
        <section className="section header-section">
            <div className="container">
                <div className="title">
                    <span>Blue CommerceTech</span>
                    <h1>Blue Horizon</h1>
                    <span>Onde o oceano de sustentabilidade encontra as ondas do comércio</span>
                </div>
                <div className="buttons">
                    <Link href="/sign-in-page" className="sign-in-button">Cadastre-se</Link>
                    <Link href="/login-page" className="login-button">Login</Link>
                </div>
            </div>
        </section>
        </>
    );
};

export default HeaderSection;