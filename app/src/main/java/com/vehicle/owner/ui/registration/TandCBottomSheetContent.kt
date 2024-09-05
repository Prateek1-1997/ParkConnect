package com.vehicle.owner.ui.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TandCBottomSheetContent(
    modifier: Modifier = Modifier,
) {

    TandCContent(modifier.fillMaxWidth())
}

@Composable
fun  TandCContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
        modifier
            .background(Color.White)
            .padding(vertical = 16.dp),
    ) {
        Text(
            text = "Terms and Conditions",
            style = TextStyle.Default,
            modifier =
            Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        )
        Divider(
            thickness = 1.dp,
            color = Color.Gray,
        )
        Text(
            text = "Welcome to Hellowner!\n" +
                    "\n" +
                    "These terms and conditions outline the rules and regulations for the use of Hellowner's Website and Mobile Application.\n" +
                    "By accessing this website and/or using our mobile application, we assume you accept these terms and conditions. Do not continue to use Hellowner if you do not agree to take all of the terms and conditions stated on this page.\n" +
                    "\n" +
                    "The following terminology applies to these Terms and Conditions, Privacy Statement, and Disclaimer Notice and all Agreements: \"Client,\" \"You,\" and \"Your\" refer to you, the person logging on this website and compliant to the Company’s terms and conditions. \"The Company,\" \"Ourselves,\" \"We,\" \"Our,\" and \"Us\" refer to Hellowner. \"Party,\" \"Parties,\" or \"Us\" and our branch name which is our Car App refer to WhoisCar, referring to both the Client and ourselves. All terms refer to the offer, acceptance, and consideration of payment necessary to undertake the process of our assistance to the Client in the most appropriate manner for the express purpose of meeting the Client’s needs in respect to the provision of the Company’s stated services, in accordance with and subject to, prevailing law of Netherlands. Any use of the above terminology or other words in the singular, plural, capitalization, and/or he/she or they, are taken as interchangeable and therefore as referring to the same.\n" +
                    "\n" +
                    "\n" +
                    "The Car App constitutes a mobile application through which individuals can access diverse features associated with their vehicles. By utilizing the Car App, users acknowledge and signify their agreement to the following stipulations and provisions:\n" +
                    "Age Qualification: Individuals must have attained the age of eighteen (18) years to be eligible for using the Car App. This requirement serves to ensure that users possess the legal capacity to enter into a binding contractual arrangement and bear accountability for their actions.\n" +
                    "Lawful Utilization: The Car App is solely intended for lawful purposes, including obtaining vehicular information, scheduling maintenance appointments, and effectuating payments. Engaging in illegal or unauthorized activities, such as hacking, distributing unsolicited electronic communications (spamming), or disseminating malicious software (malware), is strictly prohibited.\n" +
                    "Prohibition of Harassment and Intimidation: The Car App must not be utilized as a means to harass, intimidate, or harm others. This includes transmitting abusive or threatening messages, initiating unwanted telephone calls, or engaging in any other forms of harassment.\n" +
                    "Accuracy of Information: Users are prohibited from disseminating false or misleading information through the Car App. This includes posting fabricated reviews, spreading rumors, or sharing inaccurate data concerning the Car App or its services.\n" +
                    "Adherence to Legal Obligations: All users are obligated to abide by all applicable laws and regulations while utilizing the Car App. This entails adhering to traffic regulations, following established rules of the road, and complying with other legal requirements.\n" +
                    "Responsibility for User-Generated Content: Users bear sole responsibility for the content they post on the Car App, including comments, reviews, and other submissions. It is imperative that users ensure that their content is accurate, respectful, and in compliance with all relevant laws and regulations. Visitors may post content as long as it is not obscene, illegal, defamatory, threatening, infringing of intellectual property rights, invasive of privacy, or injurious in any other way to third parties. Content has to be free of software viruses, political campaigns, and commercial solicitation.\n" +
                    "We reserve all rights (but not the obligation) to remove and/or edit such content. When you post your content, you grant Hellowner non-exclusive, royalty-free, and irrevocable right to use, reproduce, publish, modify such content throughout the world in any media.\n" +
                    "Removal of Content: We reserve the right to remove any content deemed inappropriate or potentially harmful. Such content may include offensive, hateful, discriminatory material or content that violates our terms and conditions.\n" +
                    "Termination of Accounts: We reserve the right to terminate user accounts at any time, for any reason. This includes instances where users violate our terms and conditions, engage in unlawful or unauthorized activities, or when we have reason to believe that an account is being utilized in a manner that poses harm to others.\n" +
                    "Privacy Policy\n" +
                    "\n" +
                    "We collect the following information from our users:\n" +
                    "Personal Data: We collect users' names, email addresses, and vehicle numbers. This information is utilized to establish user accounts and grant access to the Car App's functionalities.\n" +
                    "Chat Records: Conversations between users and our customer support team are retained as chat history. This information is utilized to provide support and enhance the quality of our customer service.\n" +
                    "The aforementioned information is collected with the sole purpose of providing users with the best possible experience on the Car App. We will not divulge users' information to third parties without their explicit consent, except as required by law.\n" +
                    "\n" +
                    "Security\n" +
                    "\n" +
                    "The security of users' information is a matter of utmost importance to us. We employ industry-standard security measures to protect users' information from unauthorized access, utilization, or disclosure. These measures include encryption, firewalls, and other cutting-edge security technologies.\n" +
                    "\n" +
                    "Cookies\n" +
                    "\n" +
                    "We employ the use of cookies. By accessing Hellowner Aka Whoiscar/Car App, you agree to use cookies in agreement with Hellowner's Privacy Policy.\n" +
                    "Most interactive websites use cookies to let us retrieve the user’s details for each visit. Cookies are used by our website to enable the functionality of certain areas to make it easier for people visiting our website. Some of our affiliate/advertising partners may also use cookies.\n" +
                    "\n" +
                    "\n" +
                    "License\n" +
                    "\n" +
                    "Unless otherwise stated, Hellowner and/or its licensors own the intellectual property rights for all material on Hellowner. All intellectual property rights are reserved. You may access this from Hellowner for your own personal use subject to restrictions set in these terms and conditions.\n" +
                    "You must not:\n" +
                    "Republish material from Hellowner\n" +
                    "Sell, rent, or sub-license material from Hellowner\n" +
                    "Reproduce, duplicate, or copy material from Hellowner\n" +
                    "Redistribute content from Hellowner\n" +
                    "This Agreement shall begin on the date hereof.\n" +
                    "\n" +
                    "Communications\n" +
                    "\n" +
                    "The entire communication with us is electronic. Every time you send us an email or visit our website, you are going to be communicating with us. You hereby consent to receive communications from us. If you subscribe to the news on our website/app, you are going to receive regular emails from us. We will continue to communicate with you by posting news and notices on our website and by sending you emails. You also agree that all notices, disclosures, agreements, and other communications we provide to you electronically meet the legal requirements that such communications be in writing.\n" +
                    "\n" +
                    "License and Site Access\n" +
                    "\n" +
                    "We grant you a limited license to access and make personal use of Hellowner. You are not allowed to download or modify it. This may be done only with written consent from us.\n" +
                    "\n" +
                    "User Account\n" +
                    "\n" +
                    "If you are an owner of an account on Hellowner, you are solely responsible for maintaining the confidentiality of your private user details (username and password). You are responsible for all activities that occur under your account or password.\n" +
                    "We reserve all rights to terminate accounts, edit or remove content, and cancel orders at our sole discretion.\n" +
                    "\n" +
                    "\n" +
                    "Changes to This Agreement\n" +
                    "\n" +
                    "We reserve the right, at our sole discretion, to modify or replace these Terms and Conditions by posting the updated terms on Hellowner. Your continued use of Hellowner after any such changes constitutes your acceptance of the new Terms and Conditions.\n" +
                    "\n" +
                    "\n" +
                    "Contact Information\n" +
                    "\n" +
                    "For any inquiries or concerns regarding these terms and conditions or our privacy policy, please feel free to contact us.\n",
            modifier =
            Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp),
        )

    }
}


@Preview
@Composable
fun EditEmailBottomSheetPreview() {
    TandCContent(modifier = Modifier)
}
