import Image from "next/image";
import ParagraphSection from "@/components/Sections/ParagraphSection";
import HeaderSection from "@/components/Sections/HeaderSection";

export default function Home() {
  return (
    <main className="flex">
      <HeaderSection/>
      <ParagraphSection 
        text="Lorem ipsum dolor, sit amet consectetur adipisicing elit. 
          Ex quos, omnis et ipsam accusamus nulla velit sed error aliquid, 
          mollitia saepe doloremque, quas sunt ratione odit. 
          Provident obcaecati beatae officia! Lorem ipsum dolor, 
          sit amet consectetur adipisicing elit. 
          Ex quos, omnis et ipsam accusamus nulla velit sed error aliquid, 
          mollitia saepe doloremque, quas sunt ratione odit. 
          Provident obcaecati beatae officia! Lorem ipsum dolor, 
          sit amet consectetur adipisicing elit. 
          Ex quos, omnis et ipsam accusamus nulla velit sed error aliquid, 
          mollitia saepe doloremque, quas sunt ratione odit. 
          Provident obcaecati beatae officia!"
        style=""
      />
    </main>
  );
}
