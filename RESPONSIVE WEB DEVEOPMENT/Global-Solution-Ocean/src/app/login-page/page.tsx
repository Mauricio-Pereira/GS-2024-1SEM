import LoginSection from "@/components/Sections/LoginSection";
import ParagraphSection from "@/components/Sections/ParagraphSection";
import Image from "next/image";

export default function LoginPage() {
  return (
    <main className="flex">
      <ParagraphSection
        text="Faça seu Login Aqui"
      />
      <LoginSection/>
    </main>
  );
}
