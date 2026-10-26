# VG Shores Hotel Management System

A Java Swing hotel management system created as a school assignment. It supports staff sign-in and management of guests, rooms, and bookings. The program stores its data in local text files.

## Requirements

- Java Development Kit (JDK) 8 or later
- Git (for uploading to GitHub)

## Run the application

Open a terminal in this folder and run:

```powershell
javac *.java
java Main
```

On macOS or Linux, use the same commands in a terminal. Run them from the project folder because the application reads `Guest.txt`, `Staff.txt`, `Room.txt`, and `Booking.txt` from its working directory. The supplied data is fictional demo data.

### Demo sign-in

- Staff ID: `S001`
- Password: `demo123`

Change this demo password before sharing a live deployment. This school project stores passwords as plain text and is intended for demonstration, not real hotel operations.

## Data files

Each row uses `@_@` as the field separator. The app may update these files while it runs. Keep a backup if you add data you want to preserve.

## Presentation video

Add your YouTube URL here:

`[Paste the presentation video link](https://www.youtube.com/watch?v=VIDEO_ID)`

Replace the example URL above with your actual public or unlisted video link. Do not include a private or school-restricted link unless viewers have access.

## Upload this project to GitHub

1. Sign in to GitHub and create a **new empty repository** (for example, `vg-shores-hotel`). Do not initialize it with a README, since this folder already contains one.
2. Open a terminal in this project folder and run, replacing the URL with your repository's HTTPS URL:

```bash
git init
git add .
git commit -m "Add VG Shores hotel management system"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/vg-shores-hotel.git
git push -u origin main
```

Before publishing, confirm you have permission to share the project and its images, and make sure you have not added real people's personal information, passwords, or school-confidential material. The repository data files here contain fictional demo records.
