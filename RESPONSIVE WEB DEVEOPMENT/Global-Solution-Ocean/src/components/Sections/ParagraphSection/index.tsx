import { ParagraphSectionProps} from "./interface";
import '../sections_style.css';

const ParagraphSection = (props: ParagraphSectionProps) => {
    return(
        <>
        <section className="section paragraph-section">
            <div className="container">
                <p>{props.text}</p>
            </div>
        </section>
        </>
    );
};

export default ParagraphSection;