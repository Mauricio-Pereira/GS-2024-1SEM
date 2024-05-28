import { SectionProps } from "../interface";
import '../sections_style.css';

const ParagraphSection = (props: SectionProps) => {
    return(
        <>
        <section className="section paragraph-section">
            <div className={props.style}>
                <p>{props.text}</p>
            </div>
        </section>
        </>
    );
};

export default ParagraphSection;