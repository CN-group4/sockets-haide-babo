import socket

HOST = '0.0.0.0'
PORT = 5001

def main():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    server_socket.bind((HOST, PORT))

    print(f"[UDP Server] Ascult pe portul {PORT}...")
    print("[UDP Server] Astept primul mesaj de la client...\n")

    client_addr = None

    try:
        while True:
            data, addr = server_socket.recvfrom(1024)
            if client_addr is None:
                client_addr = addr
                print(f"[UDP Server] Client detectat: {client_addr}\n")

            mesaj_primit = data.decode('utf-8').strip()
            print(f"[Client]: {mesaj_primit}")

            if mesaj_primit.lower() == 'exit':
                print("[UDP Server] Clientul a trimis 'exit'. Închid.")
                break

            raspuns = input("[Tu (Server)]: ")
            server_socket.sendto((raspuns + "\n").encode('utf-8'), client_addr)

            if raspuns.strip().lower() == 'exit':
                print("[UDP Server] Ai trimis 'exit'. Închid.")
                break

    finally:
        server_socket.close()
        print("[UDP Server] Socket închis.")

if __name__ == '__main__':
    main()