import { SectionProps } from "../interface";
import '../sections_style.css';

const HeaderSection = (props: SectionProps) => {
    return(
        <>
        <section className="section header-section">
            <div className={props.style}>
                <p>{props.text}</p>
            </div>
        </section>
        </>
    );
};

export default HeaderSection;