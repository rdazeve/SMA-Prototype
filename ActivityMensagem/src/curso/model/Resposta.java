package curso.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Resposta implements Parcelable {

	private Long asw_id;
	private Long ask_id;
	private Long user_id;
	private String user_name;
	private String asw_resposta;

	public String getAsw_resposta() {
		return asw_resposta;
	}

	public void setAsw_resposta(String asw_resposta) {
		this.asw_resposta = asw_resposta;
	}

	public Long getAsw_id() {
		return asw_id;
	}

	public void setAsw_id(Long asw_id) {
		this.asw_id = asw_id;
	}

	public Long getAsk_id() {
		return ask_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public void setAsk_id(Long ask_id) {
		this.ask_id = ask_id;
	}

	public Resposta(Parcel in) {
		this.asw_id = in.readLong();
		this.ask_id = in.readLong();
		this.user_id = in.readLong();
		this.asw_resposta = in.readString();
	}

	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.getAsw_id());
		dest.writeLong(this.getAsk_id());
		dest.writeLong(this.getUser_id());
		dest.writeString(this.getAsw_resposta());
	}

	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Resposta createFromParcel(Parcel in) {
			return new Resposta(in);
		}

		public Pergunta[] newArray(int size) {
			return new Pergunta[size];
		}
	};

	public Resposta() {
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	};

}