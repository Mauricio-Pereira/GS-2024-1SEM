import ParagraphSection from "@/components/Sections/ParagraphSection";
import SingInSection from "@/components/Sections/SignInSection";
import Image from "next/image";

export default function SignInPage() {
  return (
    <main className="flex">
      <ParagraphSection
        text="Faça já o seu Cadastro"
      />
      <SingInSection/>
    </main>
  );
}
