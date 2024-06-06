import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";

import Header from "../components/Header";
import Footer from "../components/Footer";
import KeyboardNav from "../components/KeyBoardNav";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "Blue Horizon Market",
  description: "Onde o oceano de sustentabilidade encontra as ondas do comércio",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className={inter.className}>
        <KeyboardNav>
        <Header/>
        {children}
        <Footer/>
        </KeyboardNav>
        </body>
    </html>
  );
}
