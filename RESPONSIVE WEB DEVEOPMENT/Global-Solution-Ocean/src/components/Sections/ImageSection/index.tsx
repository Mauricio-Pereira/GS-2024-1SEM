import Image from "next/image";
import { ImgSectionProps} from "./interface";
import '../sections_style.css';
import './imageSection_style.css';

const ImageSection = (props: ImgSectionProps) => {
    return(
        <>
        <section className={"section image-section" + " " + props.style}>
            <div className="container">
                <Image className="img" src={props.src} width={props.width} height={props.height} alt={props.alt} title={props.title}/>
            </div>
        </section>
        </>
    );
};

export default ImageSection;